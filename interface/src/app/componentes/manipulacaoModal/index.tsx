import { Box, Button, Typography, Dialog, DialogTitle, DialogContent, Tabs, Tab, TextField, Divider } from "@mui/material";
import { useState } from "react";
import { FaSlidersH } from "react-icons/fa";
import { TabContext, TabPanel, TabList } from "@mui/lab";
import VizinhosTab from "./tabs/vizinhos";
import AvgTab from "./tabs/avg";
import CaminhoMinimoTab from "./tabs/caminhoMinimo";
import RemoverTab from "./tabs/remover";

export default function ManipulacaoModal(){
    const [open, setOpen] = useState<boolean>(false);
    const [value, setValue] = useState("1");
    const handleChange = (event: React.SyntheticEvent, newValue: string) => {
        setValue(newValue);
      };

    return(
    <div>
        <Button color='info' variant='outlined' sx={{my:2}} startIcon={<FaSlidersH/>} fullWidth onClick={() => setOpen(true)}>Manipular Grafo</Button>
        <Dialog
        fullWidth
        maxWidth={'md'}
        open={open}
        onClose={() => setOpen(false)}
        aria-labelledby="parent-modal-title"
        aria-describedby="parent-modal-description"
        >
            <DialogTitle id="alert-dialog-title">Manipular Grafo - Interações</DialogTitle>
            <Divider />
            <DialogContent>
                <Box sx={{w:100}}>                
                    <TabContext value={value}>
                        <TabList onChange={handleChange} value={value}>
                            <Tab label="Grau e Vizinhos" value="1"/>
                            <Tab label="Arvore Geradora" value="2"/>
                            <Tab label="Caminho Mínimo" value="3"/>
                            <Tab label="Remover" value="4"/>
                        </TabList>
                        <Divider/>
                        <TabPanel value="1">
                            <VizinhosTab/>
                        </TabPanel>
                        <TabPanel value="2">
                            <AvgTab/>
                        </TabPanel>
                        <TabPanel value="3">
                            <CaminhoMinimoTab/>
                        </TabPanel>
                        <TabPanel value="4">
                            <RemoverTab/>
                        </TabPanel>
                    </TabContext>
                </Box>
            </DialogContent>
        </Dialog>
    </div>
    )
}