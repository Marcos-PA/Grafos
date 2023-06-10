'use client'
import './page.module.css'
import { Box } from '@mui/material';
import {Button, Divider, Grid, Typography} from '@mui/material'
import dynamic from 'next/dynamic';
import { useEffect, useRef, useState } from 'react';
import { FaPlusSquare } from 'react-icons/fa'
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
    setGraph({
      nodes: [
        { id: 1, label: "Brasil", title: "População: 2000000", image:"https://t.ctcdn.com.br/JgnBJ9FG-tayP1V7Id8MPVcQW7w=/512x288/smart/filters:format(webp)/i659457.jpeg", shape: "circularImage" },
        { id: 3, label: "Brasil", title: "node 3 tootip text", image:"https://t.ctcdn.com.br/JgnBJ9FG-tayP1V7Id8MPVcQW7w=/512x288/smart/filters:format(webp)/i659457.jpeg", shape: "circularImage" },
        { id: 2, label: "Brasil", title: "node 2 tootip text", image:"https://t.ctcdn.com.br/JgnBJ9FG-tayP1V7Id8MPVcQW7w=/512x288/smart/filters:format(webp)/i659457.jpeg", shape: "circularImage" },
        { id: 4, label: "Brasil", title: "node 4 tootip text", image:"https://t.ctcdn.com.br/JgnBJ9FG-tayP1V7Id8MPVcQW7w=/512x288/smart/filters:format(webp)/i659457.jpeg", shape: "circularImage" },
        { id: 5, label: "Brasil", title: "node 5 tootip text", image:"https://t.ctcdn.com.br/JgnBJ9FG-tayP1V7Id8MPVcQW7w=/512x288/smart/filters:format(webp)/i659457.jpeg", shape: "circularImage" }
      ],
      edges: [
        { from: 1, to: 2 },
        { from: 1, to: 3 },
        { from: 2, to: 4 },
        { from: 2, to: 5 }
      ]   
    })
  }, []);

  return (
    <Grid className="main" sx={{display:'flex', justifyContent:'center', alignItems:'center'}}>
      <Grid item md={12} sx={{textAlign:'center'}}>
        <Typography fontSize={28} sx={{mt:2}}>Visualização do grafo</Typography>
        <Button color='info' variant='outlined' sx={{my:2}} startIcon={<FaPlusSquare/>} fullWidth>Manipular Grafo</Button>
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