
#The call to the chatgpt api found on the /src/controller/Kontrolleri sendOstoslistaRequest() method is implemented with this backend server python code hosted at pythonanywhere.com
#This file is included in the Budjettisovellus package for reference and context in case other members of the project group want or need to implement their own servers and also for review purposes for the teachers.

import openai
from flask import Flask, request, jsonify, abort
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address
import os

openai_api_key = os.getenv("CHATGPT_API_KEY")
client_key = "CLIENT_API_KEY"

app = Flask(__name__)

limiter = Limiter(
    app=app,
    key_func=get_remote_address,  # Use the client's IP address as the identifier
    default_limits=["50 per day", "50 per hour"]  # Set default rate limits
)


@app.route('/chatgpt', methods=['POST'])
@limiter.limit("10 per minute")  # Set a custom rate limit for this endpoint
def chatgpt_proxy():
    client_api_key = request.headers.get("Client-API-Key")
    if not client_api_key or client_api_key != client_key:
        abort(401, description="Unauthorized: Invalid API key")
    # Forward the request to ChatGPT API
    prompt = request.data.decode("utf-8")
    messages = [{'role': 'system', 'content': 'You are a helpful assistant.'}, {'role': 'user', 'content': prompt}]
    response = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=messages,
        temperature=0,
    )
    # Return the response received from ChatGPT API
    return jsonify(response.choices[0].to_dict()), 200


if __name__ == '__main__':
    app.run(port=5000, debug=True)
