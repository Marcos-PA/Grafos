package Codigo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LeitorCsv{
    private List<String> listHeaders;
    private final File file;
    private Scanner fileScanner;
    private Integer lineCount;
	private String line;
	private String lendoAgora;
	private Boolean hasNextColumn = true;

    public LeitorCsv(String fileName) throws FileNotFoundException{
        this.file = new File(fileName);
        this.fileScanner = new Scanner(this.file);
        this.lineCount = 0;
        this.lerHeader();
    }

    public List<String> headers(){
        return this.listHeaders;
    }

    public Map<String, String> nextLine(){
        if(this.lineCount == 0) this.lerHeader();

        Map<String, String> mapLinha = new HashMap<>();
        List<String> nextLineColumns = this.getColumnsOfLine(this.lerLinha());
        if(nextLineColumns.size() == 0) return mapLinha;

        for(Integer i = 0;i < this.headers().size(); i++){
            mapLinha.put(this.headers().get(i), nextLineColumns.get(i));
        }

        return mapLinha;
    }

    public boolean hasNextLine(){ 
        return this.fileScanner.hasNextLine();
    }

    public Integer lineCount(){ 
        return this.lineCount;
    }

    private void lerHeader(){
        this.listHeaders = this.getColumnsOfLine(this.lerLinha());
    }

    private String lerLinha(){
        if(this.fileScanner.hasNextLine()){
            this.lineCount++;
            return this.fileScanner.nextLine();
        }else{
            return null;
        }
    }

    private List<String> getColumnsOfLine(String line){
        this.lendoAgora = this.line = line;
        List<String> listColumns = new ArrayList<>();
        if(line == null) return listColumns;
        this.hasNextColumn = true;

        while(this.getHasNextColumn()){
            listColumns.add(this.getNextColumn());
        }

        return listColumns;
    }

	private Boolean getHasNextColumn(){
		return this.hasNextColumn;
	}

	private String getNextColumn(){
		String[] splittedLine = this.lendoAgora.split(",", 2);
		if(splittedLine.length == 1){
			this.hasNextColumn = false;
			return this.lendoAgora;
		}
		
		String column = splittedLine[0];
		if(this.lendoAgora.charAt(0) == '"'){
			column = "";
			this.lendoAgora = this.lendoAgora.substring(1, this.lendoAgora.length());
			while(this.lendoAgora.charAt(0) != '"'){
				column += this.lendoAgora.charAt(0);
				this.lendoAgora = this.lendoAgora.substring(1, this.lendoAgora.length());
			}
			this.lendoAgora = this.lendoAgora.substring(this.lendoAgora.length() > 1 ? 2 : 1, this.lendoAgora.length());
		}else{
			this.lendoAgora = splittedLine[1];
		}
		
		return column;
	}
}