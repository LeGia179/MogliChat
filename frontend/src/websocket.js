// frontend/src/websocket.js

let socket;

export function connect() {
    socket = new WebSocket('ws://localhost:3000');

    socket.onopen = () => {
        console.log('Connected to the WebSocket server');
    };

    socket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        showMessage(message);
    };

    socket.onclose = () => {
        console.log('Disconnected from the WebSocket server');
    };
}

export function sendMessage(message) {
    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.send(JSON.stringify(message));
    }
}

function showMessage(message) {
    console.log('Received message:', message);
    // Implement your logic to display the message in the UI
}
