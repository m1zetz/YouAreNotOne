from flask import Flask, request, jsonify
from flask_cors import CORS
from passlib.hash import bcrypt 
import psycopg2
import os
import traceback

app = Flask(__name__)

DATABASE_URL = "postgresql://neondb_owner:npg_FUqQJ7C3MKyN@ep-super-math-a91968rn-pooler.gwc.azure.neon.tech/neondb?sslmode=require&channel_binding=require"

def get_connection():
    return psycopg2.connect(DATABASE_URL)

CORS(app)
#
#_________________________________________Likes_Comments__________________________________________

@app.route("/get_likes_comments", methods=["POST"])
def get_likes_comments():
    with get_connection() as conn:

        data = request.json
        comment_id = data["comment_id"]
            
        with conn.cursor() as cur:
            cur.execute("SELECT COUNT(*) FROM comments_likes WHERE comment_id = %s;",(comment_id,)) 
            like_count = cur.fetchone()[0]
            
            cur.execute("SELECT user_id FROM comments_likes WHERE comment_id = %s;",(comment_id,))
            liked_by = [r[0] for r in cur.fetchall()]

            data = {
                "count": like_count,
                "liked_by": liked_by
            }
        
            return jsonify(data)

@app.route("/add_like_comment", methods=["POST"])
def add_like_comment():
     with get_connection() as conn:

        data = request.json
        comment_id = data["comment_id"]
        user_id = data["user_id"]

        with conn.cursor() as cur:
            cur.execute("INSERT INTO comments_likes (comment_id, user_id) VALUES (%s, %s)",
                    (comment_id, user_id))
            conn.commit()
 
            return jsonify({"status": "ok"}), 200
    
@app.route("/drop_like_comment", methods=["POST"])
def drop_like_comment():
    with get_connection() as conn:

        data = request.json
        comment_id = data["comment_id"]
        user_id = data["user_id"]
            
        with conn.cursor() as cur:
            cur.execute("DELETE FROM comments_likes WHERE comment_id = %s AND user_id = %s;",(comment_id,user_id))
        
            return jsonify({"status": "ok"}), 200

#_________________________________________Likes_Posts__________________________________________

@app.route("/get_likes", methods=["POST"])
def get_likes():
    with get_connection() as conn:

        data = request.json
        post_id = data["post_id"]
            
        with conn.cursor() as cur:
            cur.execute("SELECT COUNT(*) FROM posts_likes WHERE post_id = %s;",(post_id,))
            like_count = cur.fetchone()[0]
            
            cur.execute("SELECT user_id FROM posts_likes WHERE post_id = %s;",(post_id,))
            liked_by = [r[0] for r in cur.fetchall()]

            data = {
                "count": like_count,
                "liked_by": liked_by
            }
        
            return jsonify(data)

@app.route("/add_like", methods=["POST"])
def add_like():
     with get_connection() as conn:

        data = request.json
        post_id = data["post_id"]
        user_id = data["user_id"]

        with conn.cursor() as cur:
            cur.execute("INSERT INTO posts_likes (post_id, user_id) VALUES (%s, %s)",
                    (post_id, user_id))
            conn.commit()
 
            return jsonify({"status": "ok"}), 200
    
@app.route("/drop_like", methods=["POST"])
def drop_like():
    with get_connection() as conn:

        data = request.json
        post_id = data["post_id"]
        user_id = data["user_id"]
            
        with conn.cursor() as cur:
            cur.execute("DELETE FROM posts_likes WHERE post_id = %s AND user_id = %s;",(post_id,user_id))
        
            return jsonify({"status": "ok"}), 200

#_______________________________________Comments____________________________________________


@app.route("/get_comments", methods=["GET"])
def get_comments():
     with get_connection() as conn:
        with conn.cursor() as cur:
            cur.execute("SELECT id, posts, comments FROM posts_comments")
            rows = cur.fetchall()

            posts = [{"comment_id": row[0],"post_id": row[1], "comments": row[2]} for row in rows]

            return jsonify(posts)

@app.route("/add_comment", methods=["POST"])
def add_comment():
     with get_connection() as conn:

        data = request.json
        post_id = data["postId"]
        comment = data["comment"]

        with conn.cursor() as cur:
            cur.execute("INSERT INTO posts_comments (posts, comments) VALUES (%s, %s)",
                    (post_id, comment))
            conn.commit()
 
            return jsonify({"status": "ok"}), 200

#_______________________________________Posts___________________________________________

@app.route("/get_all_posts", methods=["GET"])
def get_posts():
     with get_connection() as conn:
        with conn.cursor() as cur:
            cur.execute("SELECT id ,title, post_text, user_id FROM posts")
            rows = cur.fetchall()

            posts = [{"id": row[0],"title": row[1], "post_text": row[2], "user_id": row[3]} for row in rows]

            return jsonify(posts)
        
@app.route("/add_post", methods=["POST"])
def add_post():
    try:
        data = request.json

        user_id = data["user_id"]
        title = data["title"]
        text = data["text"]

        with get_connection() as conn:
            with conn.cursor() as cur:
                cur.execute(
                    "INSERT INTO posts (user_id, title ,post_text) VALUES (%s, %s, %s)",
                    (user_id, title, text)
                )
                conn.commit()

        return jsonify({"status": "ok"}), 200

    except Exception as e:
        traceback.print_exc()
        return jsonify({"error": "internal server error"}), 500
    

@app.route("/drop_post", methods=["POST"])
def drop_post():
    try:
        data = request.json

        post_id = data["post_id"]

        with get_connection() as conn:
            with conn.cursor() as cur:
                cur.execute(
                    "DELETE FROM posts WHERE id = %s",
                    (post_id,)
                )
                conn.commit()
        print("удаление поста с айди " + post_id)
        return jsonify({"status": "ok"}), 200

    except Exception as e:
        traceback.print_exc()
        return jsonify({"error": "internal server error"}), 500

#_______________________________________Login____________________________________________

@app.route("/register", methods=["POST"])
def register():
    data = request.json
    login = data["login"]
    password = data["password"]

    password_hash = bcrypt.hash(password)

    with get_connection() as conn:
        with conn.cursor() as cur:
            cur.execute("INSERT INTO users (login, password) VALUES (%s, %s)", (login, password_hash))
            conn.commit()

    return jsonify({"status": "registered"})

@app.route('/')
def home():
    return "Косаточка - моя вечная любовь."

@app.route("/login", methods=["POST"])
def login():
    data = request.json
    login = data.get("login")
    password = data.get("password")

    with get_connection() as conn:
        with conn.cursor() as cur:
            cur.execute("SELECT password FROM users WHERE login = %s", (login,))
            result = cur.fetchone()

            if result is None:
                return jsonify({"myId": "-2"})

            password_hash = result[0]
            if bcrypt.verify(password, password_hash):
                cur.execute("SELECT id FROM users WHERE login = %s", (login,))
                user_id = cur.fetchone()[0]
                return jsonify({"myId": user_id})
            else:
                return jsonify({"myId": "-2"})


#_____________________________________Main______________________________________________

if __name__ == "__main__":
    app.run(host='0.0.0.0',debug=True)