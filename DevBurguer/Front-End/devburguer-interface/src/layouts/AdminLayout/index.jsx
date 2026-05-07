import { Outlet, Navigate } from "react-router-dom"
import { SideNavAdmin } from "../../components"
import { Container } from './styles'

export function AdminLayout() {
    //Renomeia a propria admin para isAdmin
    
    const userDataString = localStorage.getItem('stackburguer:userData')

    let isAdmin = false;

    try {
        const userData = userDataString ? JSON.parse(userDataString) : {};
        console.log("Dados do Usuário no LocalStorage:", userData);
        isAdmin = userData?.admin;
    } catch(err){
        console.error("Erro ao processar dados do usuário", err);
        isAdmin = false;
    }

    return isAdmin ?

        (
            <Container>
                <SideNavAdmin />
                <main>
                    <section>
                        <Outlet />
                    </section>
                </main>
                
            </Container>
        )
        : <Navigate to='/login' />
}