module.exports = {
    dialect: process.env.DB_DIALECT,
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    username: process.env.DB_USERNAME,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_DATABASE,
    define: {
        timestamps: true, //vai trackear a data de criaçao e atualização do usuario criado
        underscored: true,
        underscoredAll: true,

    },
};