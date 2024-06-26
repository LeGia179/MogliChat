const express = require('express');
const https = require('https');
const WebSocket = require('ws');
const axios = require('axios');
const path = require('path');
const fs = require('fs');
const app = express();

app.use(express.static(path.join(__dirname, '../frontend/dist')));

const server = https.createServer({
    cert: fs.readFileSync('/path/to/fullchain.pem'),
    key: fs.readFileSync('/path/to/privkey.pem')
}, app);

const wss = new WebSocket.Server({ server, path: '/ws' });
//hallo
const activeUsers = [];

wss.on('connection', (ws) => {
    activeUsers.push(ws);
    console.log('A user connected');

    ws.on('message', (message) => {
        console.log(`Message received: ${message}`);

        activeUsers.forEach((user) => {
            if (user !== ws && user.readyState === WebSocket.OPEN) {
                user.send(message);
            }
        });

        const parsedMessage = JSON.parse(message);
        console.log(`Parsed message: ${JSON.stringify(parsedMessage)}`);

        axios.post('https://moglichatbackend-cbuw.onrender.com/message', parsedMessage)
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
});

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '../frontend/dist', 'index.html'));
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});
