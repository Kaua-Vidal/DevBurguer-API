/**
 * Store -> Cria dados
 * Index -> Lista todos os dados
 * Show -> Listar um dado especifico
 * Update -> Atualiza dados
 * Delete -> Remover Dados
 */

import * as Yup from 'yup';
import User from '../models/User.js';
import bcrypt from 'bcrypt'
import jwt from 'jsonwebtoken'
import authConfig from './../../config/auth.js'

class SessionController {
    async store(request, response) {
        const schema = Yup.object({
            email: Yup.string().email().required(),
            password: Yup.string().min(6).required(),
        })

        const isValid = await schema.isValid(request.body, { strict: true , abortEarly: false});

        const emailOrPasswordIncorrect = () => {
            return response.status(400).json({error: 'Email or password incorrect'});
        }

        if(!isValid){
            emailOrPasswordIncorrect()
        }
        
        const {email, password} = request.body;

        const existingUser = await User.findOne({
            where: {
                email,
            },
        });
        if (!existingUser) {
            emailOrPasswordIncorrect()
        }

        const isPasswordCorrect = await bcrypt.compare(password, existingUser.password_hash)

        if (!isPasswordCorrect) {
            emailOrPasswordIncorrect()
        }

        const token = jwt.sign({ id: existingUser.id}, authConfig.expiresIn , 
            {expiresIn: authConfig.expiresIn})

        return response.status(200).json({ 
            id: existingUser.id,
            name: existingUser.name,
            email: existingUser.email,
            admin: existingUser.admin,
            token
        });
    }
}

export default new SessionController