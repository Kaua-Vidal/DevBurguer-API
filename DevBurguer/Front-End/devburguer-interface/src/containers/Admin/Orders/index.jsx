
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import {Row} from './row'
import { useEffect, useState } from 'react';
import {api} from '../../../services/api'
import { orderStatusOptions } from './orderStatus';
import { FilterOption, Filter } from './styles';



export function Orders() {

  //Criação de um estados onde será guardado os dados que vierem da api
  const [orders, setOrders] = useState([]);  //BACKUP  
  const [filteredOrders, setFilteredOrders] = useState([]);   //VALORES DA TELA
  const [rows, setRows] = useState([]);
  const [activeStatus, setActiveStatus] = useState(0); //GUARDAR ID DA PG DO ITEM


  //O useEffect usamos para buscar os dados da api
  //Sempre o que está após o (... }, ...) muda, ele faz uma alteração
  useEffect(() => {
    //Async (Assincrono), tem que esperar chegar os dados para executar 
    async function loadOrders() {
      const { data } = await api.get('/orders');

      setOrders(data)
      setFilteredOrders(data)
    }

    loadOrders()
  }, [])

  
//Formato especifico da tabela
function createData(order) {
  return {
    name: order.user.name,
    orderId: order._id,
    date: order.createdAt,
    status: order.status,
    products: order.products,
    
  };
}

//Sempre que [orders] tiver alguma alteração, ele chama o que está
//dentro do useEffect
useEffect(() => {
    const newRows = filteredOrders.map( order => createData(order));

    setRows(newRows)
  }, [filteredOrders])


  function handleStatus(status) {
    if (status.id === 0) {
      setFilteredOrders(orders);
    } else {
      const newOrders = orders.filter((order) => order.status === status.value);
      setFilteredOrders(newOrders)
    }

    setActiveStatus(status.id)
  }

  useEffect(() => {
    if (activeStatus === 0) {
      setFilteredOrders(orders);
    } else {
      const statusIndex = orderStatusOptions.findIndex(item => item.id === activeStatus);
                                                                            //'approved', 'rejected'...
      const newFilteredOrders = orders.filter( order => order.status === orderStatusOptions[statusIndex].value)
      setFilteredOrders(newFilteredOrders)
    }
    

  }, [orders])

  return (
    //FRAGMENT (<> - </>): Serve para cumprir a regra do react
    //$ -> Usa-se quando o parametro será usado apenas o StyledComponents
    <>
    <Filter>
      {orderStatusOptions.map(status => (
        <FilterOption 
        key={status.id}
        onClick={() => handleStatus(status)}
        $isActiveStatus={activeStatus === status.id}
        >
          {status.label}</FilterOption>
      ))}
      
    </Filter>

    <TableContainer component={Paper}>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <TableCell />
            <TableCell >Pedido</TableCell>
            <TableCell >Cliente</TableCell>
            <TableCell >Data do Pedido</TableCell>
            <TableCell >Status</TableCell>

          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <Row key={row.orderId} row={row} orders={orders} setOrders={setOrders}/>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    </>
  );
}