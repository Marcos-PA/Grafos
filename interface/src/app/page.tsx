'use client'
import './page.module.css'
import { Backdrop, Box, CircularProgress, LinearProgress, Table, TableBody, TableCell, TableHead, TableRow, TextField } from '@mui/material';
import {Button, Divider, Grid, Typography} from '@mui/material'
import dynamic from 'next/dynamic';
import { useEffect, useRef, useState } from 'react';
import { FaTrashRestore, FaInfo } from 'react-icons/fa';
import { UUID, randomUUID } from 'crypto';
import ManipulacaoModal from './componentes/manipulacaoModal';
const VisNetwork = dynamic(() => import ('react-vis-network-graph'), { ssr: false });

interface nodeGraph {
  nodes?: {
    id: number,
    label: string,
    title: string,
    image: string,
    shape:string
  }[],
  edges?: {
    from: any,
    to: any
  }[]
}
const options = {
  autoResize:true,
  physics:{
    enabled: true,
  },
  layout:{
    improvedLayout:true
  },
  nodes:{
    shapeProperties: {
      useBorderWithImage: true,
      borderRadius: 50,
    },
    physics: false,
    borderWidth: 1,
    size: 35,
    fixed: {
      x: false,
      y: false,
    },
   color: {
      border: '#0288D0',
      background: '#0288D0',
      highlight: {
        border: '#4d010d',
        background: '#f5142e'
      },
      hover: {
        border: '#4d010d',
        background: '#f5142e'
      }
    },
    font: {
      color: '#ffff',
      size: 16, // px
      face: 'arial',
      strokeColor: '#ffffff',
      align: 'center',
      multi: false,
      bold: {
        color: '#343434',
        size: 14, // px
        face: 'arial',
        vadjust: 0,
        mod: 'bold'
      },      
    },
    shadow:{
      enabled: true,
      color: 'rgba(255,0,0,0.2)',
      size:10,
      x:5,
      y:5
    },    
  },
  edges: {
    arrows:{
      from:{
        enabled:false
      },
      to:{
        enabled:false
      }
    },
    color: {
      color: '#2B7CE9',
      highlight: "#4d010d"
    },
    shadow:{
      enabled: true,
      color: {
        color: '#ffff',
        highlight: "#ff0008"
      },
      size:20,
      x:10,
      y:10
    },
    font: {
      size: 20, // px
    },
    width:2,
  },
  height: "700px"
};

interface Cidade {
  nome: string,
  nomeAscii: string,
  latitude: number,
  longitude: number,
  pais: string,
  isoPais: string,
  estado: string,
  populacao: number,
  id: number,
}

interface Vertice {
  cidade: Cidade,
  arestas: Array<Object>,
  vizinhos: Array<Cidade>,
  grau: number
}

export default function Home() {
  const [graph, setGraph] = useState<nodeGraph>({nodes:[], edges:[]});
  const [vertice, setVertice] = useState<Vertice>();
  const [networkGrafo, setNetworkGrafo] = useState(null);
  const dataFetchedRef = useRef(false);
  const [displayProgressBar, setDisplayProgressBar] = useState(true);
  let cidades = [];
  const buscarGrafo = async () => {

    setDisplayProgressBar(true);
    fetch('http://localhost:8080/get-grafo')
    .then(res => res.json()
      .then(
        grafo => {
          let nodes = [];
          let edges = [];
          cidades = [];
          nodes = grafo.vertices.map(v => {
            cidades.push(v.cidade);
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
          
          // setGraph({...graph, nodes: nodes});
          
          edges = grafo.arestas.map(a => {
            return {
              id: a.origem + "-" + a.destino,
              from: a.origem,
              to: a.destino,
              label: a.peso
            }
          });

          let graphData = {nodes: nodes, edges: edges};
          if(networkGrafo != null){
            networkGrafo.setData(graphData);
          }else{
            setGraph(graphData);
          }
          dataFetchedRef.current = false;
        }
      )  
    );
  }

  useEffect(() => {
    buscarGrafo();
  }, []);

  function buscarVertice(verticeId) {
    fetch('http://localhost:8080/get-vertice/' + verticeId)
    .then(res => res.json()
      .then(
        (res: Vertice) => {
            if(res.cidade != null){
                setVertice(res);
            }else{
                
            }
        }
      
      )  
    );
  }

  return (
    <Grid className="main" sx={{display:'flex', justifyContent:'center', alignItems:'center'}}>
      <Grid item md={12} sx={{textAlign:'center'}}>
        <Typography fontSize={28} sx={{mt:2}}>Virtualização do Grafo</Typography>
        <Divider color='white'/>
        <Grid container spacing={3}>
          <Grid item md={4}>
            <ManipulacaoModal network={networkGrafo} cidades={cidades}/>
          </Grid>
          <Grid item md={4}>
            <Button color='error' onClick={buscarGrafo} variant='outlined' sx={{my:2}} startIcon={<FaTrashRestore/>} fullWidth>Resetar</Button>
          </Grid>
          <Grid item md={4}>
            <a href="https://github.com/Marcos-PA/Grafos/blob/master/Documentação/Documentacao-Trabalho-Grafos.pdf" target='blank'>
              <Button color='warning' onClick={buscarGrafo} variant='outlined' sx={{my:2}} startIcon={<FaInfo/>} fullWidth>Informações</Button>
            </a>
          </Grid>
        </Grid>
        <Box border={2} sx={{width: '50vw', display: 'flex', flexDirection: 'column', justifyContent: 'center', alignContent: 'center', minHeight: '700px'}}>
          <Backdrop
            open={displayProgressBar}>
            <CircularProgress />
          </Backdrop>
          <VisNetwork
            graph={graph}
            options={options}
            getNetwork = {network => {
              setDisplayProgressBar(false);
              setNetworkGrafo(network);
              network.off("selectNode");
              network.on("selectNode", (e) => buscarVertice(e.nodes[0]))
              network.off("afterDrawing");
              network.on("afterDrawing", e => setDisplayProgressBar(false))
            }}/>
          <Typography sx={{display: vertice ? 'block' : 'none'}}>ID Vértice: {vertice?.cidade.id} | Nome cidade: {vertice?.cidade.nome} | População: {vertice?.cidade.populacao.toLocaleString()}</Typography>
        </Box>
        
        {/* <TextField id="id-vertice" onChange={e => setFilter(e.target.value)} label="Vertice" variant="standard" color="info" fullWidth/> */}
        {/* <Table aria-label="Vizinhos" sx={{w:"100", my:3}}>
          <TableHead>
              <TableRow>
                  <TableCell># Id</TableCell>
                  <TableCell>Cidade</TableCell>
              </TableRow>
          </TableHead>
          <TableBody>
          {rows.map((row) => (
              <TableRow key={row?.id}>
                  <TableCell align="left">{row.id}</TableCell>
                  <TableCell align="left">{row.nome}</TableCell>
                  <TableCell align="left">
                      <Button data-target={row.id}>Teste</Button>
                  </TableCell>
              </TableRow>
          ))}
          </TableBody>
        </Table> */}
      </Grid>
    </Grid>
  )
}