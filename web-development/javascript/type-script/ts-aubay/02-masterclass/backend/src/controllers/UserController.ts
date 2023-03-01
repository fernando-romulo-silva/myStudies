import { Request, Response } from "express";
import EmailService from "../services/EmailService";

const users = [
    {name: 'Fernando', email: 'fernando.silva@bolmail.com'},
];

export default {
    async index(req: Request, res: Response) {
        return res.json(users);
    },

    async create(req: Request, res: Response) {
        const emailService = new EmailService();
        emailService.sendMail({
           to: {
                name: 'fernando', 
                email: 'fernando.silva@bolmail.com'
            }, 
           message: {
                subject: 'Bem Vindo!', 
                body: 'Seja bem vindo!'
            }
        });

        return res.send();
    }
};