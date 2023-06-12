import { Button, Grid } from "@mui/material";

export default function BuscaTab(){
    return(
        <Grid container spacing={2}>
            <Grid item md={6}>
                <Button color="info" variant="outlined" fullWidth>Busca Profundidade</Button>
            </Grid>
            <Grid item md={6}>
                <Button color="warning" variant="outlined" fullWidth>Busca Largura</Button>
            </Grid>
        </Grid>
    )
}