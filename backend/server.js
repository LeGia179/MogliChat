const express = require('express');
const http = require('http');
const path = require('path');
const bodyParser = require('body-parser');
const app = express();

app.use(express.static(path.join(__dirname, '../frontend/dist')));
app.use(bodyParser.json());

const messages = [];
let clients = [];

app.post('/send-message', (req, res) => {
    const message = req.body;
    messages.push(message);

    // Notify all waiting clients
    clients.forEach(client => client.res.json(messages));
    clients = [];
    res.status(200).end();
});

app.get('/get-messages', (req, res) => {
    if (messages.length > 0) {
        res.json(messages);
    } else {
        clients.push({ req, res });
    }
});

const server = http.createServer(app);
const PORT = process.env.PORT || 3001;

server.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});
