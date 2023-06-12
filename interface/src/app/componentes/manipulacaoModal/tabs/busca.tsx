import { Button, Grid, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";

export default function BuscaTab({networkGrafo, cidades}){

    const [idVerticeOrigem, setIdVerticeOrigem] = useState("");
    const [nomeVerticeOrigem, setNomeVerticeOrigem] = useState("");
    
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

    const busca = (e) => {
        let type = e.target.getAttribute('data-type');
        fetch(`http://localhost:8080/${type == 'prof' ? 'busca-profundidade' : 'busca-largura'}/${idVerticeOrigem}`)
        .then(res => res.json()
            .then(
                async (ordemVertices: Array<Number>) => {
                    let edges = [];
                    let cidades = [];
                    await fetch('http://localhost:8080/get-grafo')
                    .then(async res => await res.json()
                        .then(
                            grafo => {
                                let nodes = grafo.vertices.map(v => {
                                    cidades.push(v.cidade);
                                });

                                edges = grafo.arestas.map(a => {
                                    return {
                                        id: a.origem + "-" + a.destino,
                                        from: a.origem,
                                        to: a.destino,
                                        label: a.peso
                                    }
                                });
                            }
                        )  
                    );

                    console.log(cidades);

                    let nodes = [];

                    nodes = ordemVertices.map((v, idx) => {
                        let i;
                        let cidade;
                        for(i = 0; i < cidades.length; i++){
                            cidade = cidades[i];
                            if(cidade.id == v) break;
                        }

                        return {
                            id: cidade.id,
                            label: cidade.nome,
                            title: `População: ${cidade.populacao}`,
                            shape: "circular",
                            y: -cidade.latitude * 1000,
                            x: cidade.longitude * 1000,
                            color: `#${Math.round(((idx + 1)/ordemVertices.length)*255).toString(16).padStart(2, '0')}0000`,
                            fixed: true
                        };
                    });

                    networkGrafo.setData({nodes: nodes, edges: edges});
                }
            )  
        );
    }
    
    useEffect(() => {
        buscarVerticeOrigem();
    })

    return(
        
        <Grid container spacing={2}>
            <Grid item md={12}>
                <TextField id="id-vertice-origem" onChange={e => setIdVerticeOrigem(e.target.value)} label="Id Origem" variant="standard" color="info" fullWidth/>
                <Typography sx={{display: nomeVerticeOrigem ? 'block' : 'none'}}><span style={{fontWeight:"bold"}}>Nome vertice: </span>{nomeVerticeOrigem}</Typography>
            </Grid>
            <Grid item md={6}>
                <Button color="info" variant="outlined" data-type="prof" onClick={busca} fullWidth>Busca Profundidade</Button>
            </Grid>
            <Grid item md={6}>
                <Button color="warning" variant="outlined" data-type="larg" onClick={busca} fullWidth>Busca Largura</Button>
            </Grid>
        </Grid>
    )
}