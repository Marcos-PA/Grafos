import { Alert, AlertTitle, Button, Grid, Table, TableBody, TableCell, TableHead, TableRow, TextField, Typography } from "@mui/material";
import { useState } from "react";

interface Cidade {
    nome: string,
    nomeAscii: string,
    latitude: number,
    longitude: number,
    pais: string,
    isoPais: string,
    estado: string,
    populacao: number,
    id: number,
}

interface Vertice {
    cidade: Cidade,
    arestas: Array<Object>,
    vizinhos: Array<Cidade>,
    grau: number
}

export default function VizinhosTab({networkGrafo}){
    let idVertice = "";
    const [rows, setRows] = useState<Array<Cidade>>([]);
    const [vertice, setVertice] = useState<Vertice>();
    const [errorMessage, setErrorMessage] = useState<String>("");
    const buscarVertice = () => {
        fetch('http://localhost:8080/get-vertice/' + idVertice)
        .then(res => res.json()
            .then(
                (res: Vertice) => {
                    if(res.cidade != null){
                        setVertice(res);
                        setRows([...res.vizinhos])
                    }else{
                        setErrorMessage('Vértice não encontrado');
                    }
                }
            
            )  
        );
    };

    const visualizarNoGrafo = (e) => {
        networkGrafo.focus(e.target.getAttribute('data-target'), {
            scale: 2,
            animation: true
        });
    }

    return(
        <Grid container spacing={2} >
            <Grid item md={9}>
                <Alert severity="error" sx={{display: errorMessage ? 'flex' : 'none', mb: 3}}>
                    {errorMessage}
                </Alert>
                <TextField id="id-vertice" error={!!errorMessage} onChange={e => { idVertice = e.target.value; setErrorMessage('') }} label="Vertice" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={3} sx={{display:'flex', alignSelf:'end'}}>
                <Button color={"info"} onClick={buscarVertice} variant='outlined' fullWidth size="medium">Visualizar</Button> 
            </Grid>
            <Grid item md={12} sx={{display:'flex', alignSelf:'end'}}>
                <Typography sx={{display: vertice != null ? 'block' : 'none'}}>
                    <span style={{fontWeight: "bold"}}>Nome cidade:</span> {vertice?.cidade?.nome}<br/>
                    <span style={{fontWeight: "bold"}}>Grau vertice:</span> {vertice?.grau}<br/>
                    <span style={{fontWeight: "bold"}}>Quantidade de vizinhos:</span> {vertice?.vizinhos.length}<br/>
                </Typography>
            </Grid>
            <Grid item md={12}>
                <Table aria-label="Vizinhos" sx={{w:"100", my:3}}>
                    <TableHead>
                        <TableRow>
                            <TableCell># Id</TableCell>
                            <TableCell>Cidade</TableCell>
                            <TableCell></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                    {rows?.map((row) => (
                        <TableRow key={row?.id}>
                            <TableCell align="left">{row.id}</TableCell>
                            <TableCell align="left">{row.nome}</TableCell>
                            <TableCell align="right">
                                <Button data-target={row.id} onClick={visualizarNoGrafo}>Ver no mapa</Button>
                            </TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
                </Table>
            </Grid>
        </Grid>
    )
}