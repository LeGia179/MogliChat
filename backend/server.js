// Laden Sie die Umgebungsvariablen aus der .env-Datei
require('dotenv').config();

const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const axios = require('axios');
const path = require('path');
const app = express();

app.use(express.static(path.join(__dirname, '../frontend/dist')));

const server = http.createServer(app);

const wss = new WebSocket.Server({ server, path: '/ws' });

const activeUsers = [];
const baseUrl = process.env.VITE_BACKEND_BASE_URL;
const endpoint = baseUrl + "/message";

wss.on('connection', (ws) => {
    activeUsers.push(ws);
    console.log('A user connected');

    ws.on('message', (message) => {
        console.log(`Message received: ${message}`);

        activeUsers.forEach((user) => {
            if (user !== ws && user.readyState === WebSocket.OPEN) {
                user.send(message);
                console.log(`Message sent to user: ${user}`);
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
        console.log('A user disconnected');
        const index = activeUsers.indexOf(ws);
        if (index !== -1) {
            activeUsers.splice(index, 1);
        }
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
