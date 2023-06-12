import { Button, Grid, TextField } from "@mui/material";

export default function AvgTab(){
    return(
        <Grid container spacing={2} >
            <Grid item md={9}>
                <TextField id="id-vertice" label="Vertice" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={3} sx={{display:'flex', alignSelf:'end'}}>
                <Button color={"success"} variant='outlined' fullWidth size="medium">Gerar Arvore</Button> 
            </Grid>
        </Grid>
    )
}