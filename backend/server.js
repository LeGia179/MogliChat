const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const path = require('path');
const app = express();

// Serve static files from the Vue frontend
app.use(express.static(path.join(__dirname, '../frontend/dist')));

const server = http.createServer(app);
const wss = new WebSocket.Server({ server });

const activeUsers = [];

wss.on('connection', (ws) => {
    activeUsers.push(ws);
    console.log('A user connected');

    ws.on('message', (message) => {
        console.log(`Message received: ${message}`);
        // Broadcast the message to all other connected users
        activeUsers.forEach((user) => {
            if (user !== ws && user.readyState === WebSocket.OPEN) {
                user.send(message);
            }
        });
    });

    ws.on('close', () => {
        console.log('A user disconnected');
        // Remove the user from the activeUsers array
        const index = activeUsers.indexOf(ws);
        if (index !== -1) {
            activeUsers.splice(index, 1);
        }
    });
});

// Serve the main HTML file for the Vue app
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '../frontend/dist', 'index.html'));
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});
