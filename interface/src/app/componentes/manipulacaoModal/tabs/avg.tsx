import { Button, CircularProgress, Grid, TextField, Typography } from "@mui/material";
import { useState } from "react";

export default function AvgTab({networkGrafo}){
    let [buscando, setBuscando] = useState(false);
    let idVertice;
    const buscarAgm = () => {
        setBuscando(true);
        fetch('http://localhost:8080/get-agm/' + idVertice)
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
                        label: a.peso,
                        color: '#f00'
                    }
                    });

                    networkGrafo.setData({nodes: nodes, edges: edges});
                    
                }
            ).finally(() => setBuscando(false))
        ).catch(() => setBuscando(false))
    };

    return(
        <Grid container spacing={2} >
            <Grid item md={9}>
                <TextField id="id-vertice" onChange={e => idVertice = e.target.value} label="Vertice" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={3} sx={{display:'flex', alignSelf:'end'}}>
                <Button color={"success"} disabled={buscando} onClick={buscarAgm} variant='outlined' fullWidth size="medium">
                    <Typography sx={{display: !buscando ? 'block' : 'none'}}>Gerar Arvore</Typography>
                    <CircularProgress size={20} sx={{display: buscando ? 'block' : 'none'}}/>
                </Button> 
            </Grid>
        </Grid>
    )
}