import { Button, Grid, TextField } from "@mui/material";
export default function CaminhoMinimoTab({networkGrafo}){
    return(
        <Grid container spacing={2}>
            <Grid item md={6}>
                <TextField id="id-vertice-origem" label="Vertice Origem" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={6}>
                <TextField id="id-vertice-destino" label="Vertice Destino" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={12} sx={{display:'flex', alignSelf:'end'}}>
                <Button color={"warning"} variant='outlined' fullWidth size="medium">Visualizar caminho</Button> 
            </Grid>
        </Grid>
    )
}