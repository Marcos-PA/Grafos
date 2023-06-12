import { Button, Grid, Table, TableBody, TableCell, TableHead, TableRow, TextField, Typography } from "@mui/material";
import { useState } from "react";

export default function VizinhosTab({networkGrafo}){
    let idVertice = "";
    const [rows, setRows] = useState([]);
    const [tableHeader, setTableHeader] = useState("");
    const buscarVertice = () => {
        fetch('http://localhost:8080/get-vertice/' + idVertice)
        .then(res => res.json()
            .then(
                res => {
                    console.log(res);
                    setTableHeader(`Cidade ${res.cidade.nome} possui ${res.vizinhos.length} vizinhos, grau ${res.vizinhos.length}`);
                    setRows([...res.vizinhos])
                }
            
            )  
        );
    };

    return(
        <Grid container spacing={2} >
            <Grid item md={9}>
                <TextField id="id-vertice" onChange={e => idVertice = e.target.value} label="Vertice" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={3} sx={{display:'flex', alignSelf:'end'}}>
                <Button color={"info"} onClick={buscarVertice} variant='outlined' fullWidth size="medium">Visualizar</Button> 
            </Grid>
            <Grid item md={12} sx={{display:'flex', alignSelf:'end'}}>
                <Typography>{tableHeader}</Typography>
            </Grid>
            <Grid item md={12}>
            <Table aria-label="Vizinhos" sx={{w:"100", my:3}}>
                <TableHead>
                    <TableRow>
                        <TableCell># Id</TableCell>
                        <TableCell>Cidade</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                {rows?.map((row) => (
                    <TableRow key={row?.id}>
                        <TableCell align="left">{row.id}</TableCell>
                        <TableCell align="left">{row.nome}</TableCell>
                    </TableRow>
                ))}
                </TableBody>
            </Table>
            </Grid>
        </Grid>
    )
}