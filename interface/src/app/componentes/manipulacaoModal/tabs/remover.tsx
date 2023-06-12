import { Button, Grid, TextField } from "@mui/material";
import { FaTrashAlt } from 'react-icons/fa'
export default function RemoverTab({networkGrafo}){
    return(
        <Grid container spacing={2}>
            <Grid item md={6}>
                <TextField id="id-vertice-origem" label="Vertice" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={6}>
                <TextField id="id-vertice-origem" label="Aresta" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={12}>
                <Button color="error" variant="outlined" fullWidth startIcon={<FaTrashAlt/>}>Remover</Button> 
            </Grid>
        </Grid>
    )
}