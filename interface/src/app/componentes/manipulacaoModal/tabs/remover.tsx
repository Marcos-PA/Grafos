import { Button, FormControlLabel, FormLabel, Grid, Radio, RadioGroup, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { FaTrashAlt } from 'react-icons/fa'
export default function RemoverTab({networkGrafo}){
    const [removeAresta, setRemoveAresta] = useState(false);
    const [idVerticeOrigem, setIdVerticeOrigem] = useState("");
    const [idVerticeDestino, setIdVerticeDestino] = useState("");
    const [nomeVerticeOrigem, setNomeVerticeOrigem] = useState("");
    const [nomeVerticeDestino, setNomeVerticeDestino] = useState("");
    // const [idVerticeDestino, setIdVerticeDestino] = useState("");

    const remover = () => {
        (removeAresta ? fetch(`http://localhost:8080/remove-aresta/${idVerticeOrigem}/${idVerticeDestino}`) : fetch(`http://localhost:8080/remove-vertice/${idVerticeOrigem}`))
        .then(res => res.json()
            .then(
            grafo => {
                let nodes = [];
                let edges = [];
                
                nodes = grafo.vertices.map(v => {
                return {
                    id: v.cidade.id,
                    label: v.cidade.nome,
                    title: `População: ${v.cidade.populacao}`,
                    shape: "circular",
                    y: -v.cidade.latitude * 1000,
                    x: v.cidade.longitude * 1000,
                    fixed: true
                };
                });
                
                edges = grafo.arestas.map(a => {
                return {
                    id: a.origem + "-" + a.destino,
                    from: a.origem,
                    to: a.destino,
                    label: a.peso
                }
                });

                networkGrafo.setData({nodes: nodes, edges: edges});
            }
            )  
        );
    };

    const buscarVerticeOrigem = () => {
        fetch('http://localhost:8080/get-vertice/' + idVerticeOrigem)
        .then(res => res.json()
            .then(
                (res) => {
                    setNomeVerticeOrigem(res.cidade.nome);
                }
            )  
        ).catch(() => setNomeVerticeOrigem(''))
    };

    const buscarVerticeDestino = () => {
        fetch('http://localhost:8080/get-vertice/' + idVerticeDestino)
        .then(res => res.json()
            .then(
                (res) => {
                    setNomeVerticeDestino(res.cidade.nome);
                }
            )  
        ).catch(() => setNomeVerticeDestino(''))
    };

    useEffect(() => {
        buscarVerticeOrigem();
        buscarVerticeDestino();
    })

    return(
        <Grid container spacing={2}>
            <Grid item md={12}>    
                <FormLabel id="label-tipo-remocao">Tipo de remoção</FormLabel>
                <RadioGroup
                    aria-labelledby="label-tipo-remocao"
                    defaultValue="vertice"
                    onChange={e =>  setRemoveAresta(e.target.value === 'aresta')}>
                    <FormControlLabel value="vertice" control={<Radio />} label="Vertice" />
                    <FormControlLabel value="aresta" control={<Radio />} label="Aresta" />
                </RadioGroup>
            </Grid>
            <Grid item md={12} sx={{display: !removeAresta ? 'block' : 'none'}}>
                <TextField id="id-vertice-origem" onChange={e => setIdVerticeOrigem(e.target.value)}  label="Vertice" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={12} sx={{display: removeAresta ? 'block' : 'none'}}>
                <Grid container spacing={2}>
                    <Grid item md={6}>
                        <TextField id="id-vertice-origem" onChange={e => setIdVerticeOrigem(e.target.value)} label="Id Origem" variant="standard" color="info" fullWidth/>
                        <Typography sx={{display: nomeVerticeOrigem ? 'block' : 'none'}}><span style={{fontWeight:"bold"}}>Nome vertice: </span>{nomeVerticeOrigem}</Typography>
                    </Grid>
                    <Grid item md={6}>
                        <TextField id="id-vertice-destino" onChange={e => setIdVerticeDestino(e.target.value)} label="Id Destino" variant="standard" color="info" fullWidth/>
                        <Typography sx={{display: nomeVerticeDestino ? 'block' : 'none'}}><span style={{fontWeight:"bold"}}>Nome vertice: </span>{nomeVerticeDestino}</Typography>
                    </Grid>
                </Grid>
            </Grid>
            <Grid item md={12}>
                <Button color="error" variant="outlined" onClick={remover} fullWidth startIcon={<FaTrashAlt/>}>Remover</Button> 
            </Grid>
        </Grid>
    )
}