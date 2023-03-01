// import * as express from 'express';
import express from 'express';
import cors from 'cors';
import routes from './routes';

function getUserName(): string {
    return 'fernando';
}

const userName = getUserName();

const app = express();

app.use(cors);
app.use(routes);

app.listen(3333);

