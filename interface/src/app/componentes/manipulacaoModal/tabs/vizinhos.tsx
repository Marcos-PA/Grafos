import { Button, Grid, Table, TableBody, TableCell, TableHead, TableRow, TextField } from "@mui/material";
import { useState } from "react";

export default function VizinhosTab(){
    const [rows, setRows] = useState([]);
    return(
        <Grid container spacing={2} >
            <Grid item md={9}>
                <TextField id="id-vertice" label="Vertice" variant="standard" color="info" fullWidth/>
            </Grid>
            <Grid item md={3} sx={{display:'flex', alignSelf:'end'}}>
                <Button color={"info"} variant='outlined' fullWidth size="medium">Visualizar</Button> 
            </Grid>
            <Grid item md={12}>
            <Table aria-label="Vizinhos" sx={{w:"100", my:3}}>
                <TableHead>
                    <TableRow>
                        <TableCell># Id</TableCell>
                        <TableCell>Cidade</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                {rows?.map((row) => (
                    <TableRow key={row?.id}>
                        <TableCell align="right">{row.id}</TableCell>
                        <TableCell align="right">{row.nome}</TableCell>
                    </TableRow>
                ))}
                </TableBody>
            </Table>
            </Grid>
        </Grid>
    )
}