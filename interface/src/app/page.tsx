'use client'
import './page.module.css'
import { Box } from '@mui/material';
import {Button, Divider, Grid, Typography} from '@mui/material'
import dynamic from 'next/dynamic';
import { useEffect, useRef, useState } from 'react';
import { FaSlidersH, FaInfo } from 'react-icons/fa';
import { UUID, randomUUID } from 'crypto';
const VisNetwork = dynamic(() => import('react-vis-network-graph'), { ssr: false });

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

export default function Home() {
  const [graph, setGraph] = useState<nodeGraph>({nodes:[],edges:[]});
  let networkGrafo;

  let carregarGrafo = (dados: nodeGraph) => {
    networkGrafo?.setData(dados);
  }
  
  useEffect(() => {
    fetch('http://localhost:8080/get-grafo').then(res => res.json().then(
      grafo => {
        let nodes = [];
        let edges = [];
        let maxPop = grafo.vertices.reduce((pV, cV) => cV.cidade.populacao > pV ? cV.cidade.populacao : pV, 0);
        
        nodes = grafo.vertices.map(v => {
          return {
            id: v.cidade.id,
            label: v.cidade.nome,
            title: `População: ${v.cidade.populacao}`,
            shape: "circular",
            size: Math.max(1, v.cidade.populacao/maxPop * 25),
            y: -v.cidade.latitude * 1000,
            x: v.cidade.longitude * 1000,
            fixed: true
          };
        });
        
        edges = grafo.arestas.map(a => {
          return {
            from: a.origem,
            to: a.destino,
            label: a.peso
          }
        });
        console.log({nodes, edges});

        setGraph({
          nodes, edges
        });

        // fetch('http://localhost:8080/get-agm').then(res => res.json().then(grafoAgm => {
        //   console.log(grafoAgm);
        //   let nodesAgm = [];
        //   let edgesAgm = [];
        //   let maxPop = grafoAgm.vertices.reduce((pV, cV) => cV.cidade.populacao > pV ? cV.cidade.populacao : pV, 0);
          
        //   nodesAgm = grafoAgm.vertices.map(v => {
        //     console.log(v.cidade.populacao/maxPop * 25);
        //     return {
        //       id: v.cidade.id,
        //       label: v.cidade.nome,
        //       title: `População: ${v.cidade.populacao}`,
        //       shape: "square",
        //       size: Math.max(1, v.cidade.populacao/maxPop * 25),
        //       y: -v.cidade.latitude * 1000,
        //       x: v.cidade.longitude * 1000,
        //       fixed: true
        //     };
        //   });
        //   edgesAgm = grafoAgm.arestas.map(a => {
        //     return {
        //       from: a.origem,
        //       to: a.destino,
        //       label: a.peso,
        //       color: '#f00'
        //     }
        //   });
        //   setGraph({
        //     nodes: nodesAgm, edges: edgesAgm
        //   });

          
        // }));
      }
    ));
  }, []);

  return (
    <Grid className="main" sx={{display:'flex', justifyContent:'center', alignItems:'center'}}>
      <Grid item md={12} sx={{textAlign:'center'}}>
        <Typography fontSize={28} sx={{mt:2}}>Virtualização Do Grafo</Typography>
        <Divider color='white'/>
        <Grid container spacing={3}>
          <Grid item md={6}>
            <Button color='info' variant='outlined' sx={{my:2}} startIcon={<FaSlidersH/>} fullWidth>Manipular Grafo</Button>
          </Grid>
          <Grid item md={6}>
            <Button color='warning' variant='outlined' sx={{my:2}} startIcon={<FaInfo/>} fullWidth>Informações</Button>
          </Grid>
        </Grid>
        <Box border={2}>
          <VisNetwork    
            graph={graph}
            options={options}
            getNetwork={network => {
              networkGrafo =network
            }}
          />
        </Box>
      </Grid>
    </Grid>
  )
}