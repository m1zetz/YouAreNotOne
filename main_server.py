from flask import Flask, request, jsonify
from flask_cors import CORS
from passlib.hash import bcrypt 
import psycopg2
import os

app = Flask(__name__)

DATABASE_URL = "postgresql://neondb_owner:npg_FUqQJ7C3MKyN@ep-super-math-a91968rn-pooler.gwc.azure.neon.tech/neondb?sslmode=require"

def get_connection():
    return psycopg2.connect(DATABASE_URL)

CORS(app)

@app.route("/get_comments", methods=["GET"])
def get_comments():
     with get_connection() as conn:
        with conn.cursor() as cur:
            cur.execute("SELECT posts_id, comments FROM posts_comments")
            rows = cur.fetchall()

            posts = [{"post_id": row[0], "comments": row[1]} for row in rows]

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
        print("Ошибка при обработке /add_post:")
        traceback.print_exc()
        return jsonify({"error": "internal server error"}), 500


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
                return jsonify({"status": "error", "message": "User not found"}), 404

            password_hash = result[0]
            if bcrypt.verify(password, password_hash):
                cur.execute("SELECT id FROM users WHERE login = %s", (login,))
                user_id = cur.fetchone()[0]
                return jsonify({"myId": user_id})
            else:
                return jsonify({"status": "error", "message": "Wrong password"}), 401

if __name__ == "__main__":
    port = int(os.environ.get("PORT", 5000))
    app.run(host="0.0.0.0", port=port)