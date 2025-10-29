/**
 * POST -> Criar
 * PUT(+1 dado)/PATCH (1 dado) -> Atualizar
 * GET -> Buscar
 * DELETE -> Deletar
 */

import { Router } from "express";
import UserController from "./app/controllers/UserController.js";
import SessionController from "./app/controllers/SessionController.js";

const routes = new Router();

routes.post('/users', UserController.store)
routes.post('/session', SessionController.store)

export default routes;