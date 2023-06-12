import { Button, Grid, TextField } from "@mui/material";

export default function AvgTab({networkGrafo}){
    let idVertice;
    const buscarAgm = () => {
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

                console.log(nodes, edges);
                console.log(networkGrafo);
                networkGrafo.setData({nodes: nodes, edges: edges});
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
                <Button color={"success"} onClick={buscarAgm} variant='outlined' fullWidth size="medium">Gerar Arvore</Button> 
            </Grid>
        </Grid>
    )
}