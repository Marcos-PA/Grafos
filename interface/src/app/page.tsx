'use client'
import './page.module.css'
import { Box } from '@mui/material';
import {Button, Divider, Grid, Typography} from '@mui/material'
import dynamic from 'next/dynamic';
import { useEffect, useRef, useState } from 'react';
import { FaSlidersH, FaInfo } from 'react-icons/fa';
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
      background:"black",
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

let oldArestas, oldVertices;

export default function Home() {
  const [graph, setGraph] = useState<nodeGraph>({nodes:[], edges:[]});
  let networkGrafo;
  const dataFetchedRef = useRef(false);
  
  const buscarAgm = () => {
    fetch('http://localhost:8080/get-agm')
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
          // setGraph({...graph, nodes: nodes, edges: edges});
          networkGrafo.setData({nodes: nodes, edges: edges});
          dataFetchedRef.current = false;
        }
      )  
    );
  };

  const buscarGrafo = async () => {
    fetch('http://localhost:8080/get-grafo')
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
          if(networkGrafo != undefined){
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

  return (
    <Grid className="main" sx={{display:'flex', justifyContent:'center', alignItems:'center'}}>
      <Grid item md={12} sx={{textAlign:'center'}}>
        <Typography fontSize={28} sx={{mt:2}}>Virtualização Do Grafo</Typography>
        <Divider color='white'/>
        <Grid container spacing={3}>
          <Grid item md={4}>
            <ManipulacaoModal/>
          </Grid>
          <Grid item md={4}>
            <Button color='error' onClick={buscarGrafo} variant='outlined' sx={{my:2}} startIcon={<FaInfo/>} fullWidth>Resetar</Button>
          </Grid>
          <Grid item md={4}>
            <Button color='warning' onClick={buscarGrafo} variant='outlined' sx={{my:2}} startIcon={<FaInfo/>} fullWidth>Informações</Button>
          </Grid>
        </Grid>
        <Box border={2}>
          <VisNetwork  
            graph={graph}
            options={options}
            getNetwork = {network => {
              networkGrafo = network
            }}
          />
        </Box>
      </Grid>
    </Grid>
  )
}