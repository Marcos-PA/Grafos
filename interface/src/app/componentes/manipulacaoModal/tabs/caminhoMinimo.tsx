import { Button, Grid, TextField } from "@mui/material";
export default function CaminhoMinimoTab({networkGrafo}){

    let idVerticeOrigem, idVerticeDestino;
    const buscaCaminhoMinimo = () => {
        fetch(`http://localhost:8080/get-caminho-minimo/${idVerticeOrigem}/${idVerticeDestino}`)
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
            )  
        );
    };
    return(
        <Grid container spacing={2}>
            <Grid item md={6}>
                <TextField id="id-vertice-origem"  onChange={e => idVerticeOrigem = e.target.value} label="Vertice Origem" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={6}>
                <TextField id="id-vertice-destino"  onChange={e => idVerticeDestino = e.target.value} label="Vertice Destino" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={12} sx={{display:'flex', alignSelf:'end'}}>
                <Button color={"warning"} variant='outlined' onClick={buscaCaminhoMinimo} fullWidth size="medium">Visualizar caminho</Button> 
            </Grid>
        </Grid>
    )
}