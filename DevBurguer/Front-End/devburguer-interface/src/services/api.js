import axios from 'axios'

export const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080',
})


api.interceptors.request.use( (config) => {
    const userData = localStorage.getItem('stackburguer:userData')

    const token = userData && JSON.parse(userData).token

    config.headers.authorization = `Bearer ${token}`

    return config
})

