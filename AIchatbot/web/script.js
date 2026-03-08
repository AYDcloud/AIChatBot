let chat = document.getElementById("chat");

function add(text, type) {

    let msg = document.createElement("div");
    msg.className = "msg " + type;
    msg.innerText = text;

    let time = document.createElement("div");
    time.className = "time";
    time.innerText = new Date().toLocaleTimeString();

    msg.appendChild(time);

    chat.appendChild(msg);
    chat.scrollTop = chat.scrollHeight;
}

async function send() {

    let input = document.getElementById("input");
    let text = input.value.trim();

    if (text === "") return;

    add(text, "user");

    input.value = "";

    let thinking = document.createElement("div");
    thinking.className = "msg bot";
    thinking.innerText = "AI is thinking...";
    chat.appendChild(thinking);

    let res = await fetch("/chat", {
        method: "POST",
        body: text
    });

    let reply = await res.text();

    thinking.remove();

    add(reply, "bot");
}

document.getElementById("input").addEventListener("keydown", function(e) {
    if (e.key === "Enter") {
        send();
    }
});