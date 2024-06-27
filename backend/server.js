const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const axios = require('axios');
const path = require('path');
const dotenv = require('dotenv');

dotenv.config();

const app = express();

app.use(express.static(path.join(__dirname, '../frontend/dist')));

const server = http.createServer(app);

const wss = new WebSocket.Server({ server, path: '/ws' });

const activeUsers = [];
const userConnections = {}; // Speichert die WebSocket-Verbindungen der Benutzer
const baseUrl = process.env.VITE_BACKEND_BASE_URL;
const endpoint = baseUrl + "/message";

wss.on('connection', (ws, req) => {
    const params = new URLSearchParams(req.url.split('?')[1]);
    const username = params.get('username');

    if (username) {
        userConnections[username] = ws;
        activeUsers.push({ username, ws });
        console.log(`${username} connected`);
    } else {
        console.log('Connected user has no username');
    }

    console.log(`Active users: ${activeUsers.length}`);

    ws.on('message', (message) => {
        console.log(`Message received: ${message}`);

        activeUsers.forEach((user) => {
            if (user.ws !== ws && user.ws.readyState === WebSocket.OPEN) {
                user.ws.send(message);
                console.log(`Message sent to user: ${user.username}`);
            }
        });

        const parsedMessage = JSON.parse(message);
        console.log(`Parsed message: ${JSON.stringify(parsedMessage)}`);

        axios.post(endpoint, parsedMessage)
            .then(response => {
                console.log('Message stored in database');
            })
            .catch(error => {
                console.error('Error storing message in database:', error.response ? error.response.data : error.message);
            });
    });

    ws.on('close', () => {
        console.log(`${username} disconnected`);
        const index = activeUsers.findIndex(user => user.username === username);
        if (index !== -1) {
            activeUsers.splice(index, 1);
        }
        delete userConnections[username];
        console.log(`Active users after disconnection: ${activeUsers.length}`);
    });

    ws.on('error', (error) => {
        console.error('WebSocket error:', error);
    });
});

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '../frontend/dist', 'index.html'));
});

const PORT = process.env.PORT || 3001;
server.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});
