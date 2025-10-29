/**
 * Store -> Cria dados
 * Index -> Lista todos os dados
 * Show -> Listar um dado especifico
 * Update -> Atualiza dados
 * Delete -> Remover Dados
 */

import { v4 } from "uuid";
import User from '../models/User.js'
import * as Yup from 'yup';



class UserController {
    async store(request, response) {
        const schema = Yup.object({
            name: Yup.string().required(),
            email: Yup.string().email().required(),
            password_hash: Yup.string().min(6).required(),
            admin: Yup.boolean()
        })
        try {
            schema.validateSync(request.body, {abortEarly: false, strict: true })
        } catch (err) {
            return response.status(400).json({error: err.errors});
        }
        

        const {name, email, password_hash, admin} = request.body

        const existingUser = await User.findOne({
            where: {
                email,
            },
        });
        if (existingUser) {
            return response.status(400).json({ message: "Email already taken!" })
        }

        const user = await User.create(
            {
                id: v4(),
                name,
                email,
                password_hash,
                admin
            }
        );

        return response.status(201).json({
            id: user.id,
            name: user.name,
            email: user.email,
            admin: user.admin,
        })
        }
}

export default new UserController();