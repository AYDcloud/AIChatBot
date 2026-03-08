# AI Web Chatbot (Java + Gemini API)

## Overview

This project is a web-based AI chatbot built using **Java for the backend** and **HTML, CSS, and JavaScript for the frontend**. The chatbot communicates with the **Google Gemini API** to generate intelligent responses.

The application runs on a **local Java HTTP server** and provides a modern chat interface that allows users to interact with an AI assistant through their browser.

---

## Features

* Web-based chat interface
* Java backend server
* Integration with Google Gemini AI
* Typing animation for responses
* Message timestamps
* Conversation-style UI
* Runs locally in a browser

---

## Project Structure

```
AIChatbot
│
├── src
│   └── AIChatbotServer.java
│
├── web
│   ├── index.html
│   ├── style.css
│   └── script.js
│
└── README.md
```

---

## Requirements

* Java 17 or newer
* Internet connection
* Gemini API Key

---

## Setup Instructions

1. Download or clone the project.

2. Insert your Gemini API key in:

```
src/AIChatbotServer.java
```

Replace this line:

```
PUT_NEW_GEMINI_KEY_HERE
```

with your Gemini API key.

---

## Running the Application

Open a terminal in the project folder and run:

```
javac src/AIChatbotServer.java
java -cp src AIChatbotServer
```

After starting the server you should see:

```
AI Chatbot running → http://localhost:8080
```

Open your browser and go to:

```
http://localhost:8080
```

---

## How It Works

1. The browser interface sends a message to the Java backend.
2. The Java server sends the message to the Gemini API.
3. Gemini generates an AI response.
4. The response is returned to the browser and displayed in the chat interface.

---

## Technologies Used

* Java
* HTML
* CSS
* JavaScript
* Google Gemini API

---

## Future Improvements

* Conversation history sidebar
* Markdown formatting for AI responses
* Code syntax highlighting
* Streaming AI responses
* Cloud deployment

---

## Author

Ayush Dalela
