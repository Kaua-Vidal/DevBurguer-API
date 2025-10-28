import Sequelize, { Model } from "sequelize";


// Class Model (Dentro do sequelize)
class User extends Model {
    //Usa-se static para não precisar instanciar para usar o metodo
    static init (sequelize) {
        super.init(
        {
            name: Sequelize.STRING,
            email: Sequelize.STRING,
            password_hash: Sequelize.STRING,
            admin: Sequelize.BOOLEAN
        }, 
        {
            sequelize,
            tableName: 'users'
        },
    );
    }          
}

export default User