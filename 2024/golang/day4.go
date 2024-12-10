package main

import (
	"bytes"
	"fmt"
	"os"
)

func checkRow(input [][]byte, i, j int) (count int) {
	if j < len(input[i])-3 { // look forward
		if input[i][j+1] == 'M' && input[i][j+2] == 'A' && input[i][j+3] == 'S' {
			count++
		}
	}
	if j >= 3 { // look backward
		if input[i][j-1] == 'M' && input[i][j-2] == 'A' && input[i][j-3] == 'S' {
			count++
		}
	}
	return
}

func checkCol(input [][]byte, i, j int) (count int) {
	if i < len(input)-3 { // look forward
		if input[i+1][j] == 'M' && input[i+2][j] == 'A' && input[i+3][j] == 'S' {
			count++
		}
	}
	if i >= 3 { // look backward
		if input[i-1][j] == 'M' && input[i-2][j] == 'A' && input[i-3][j] == 'S' {
			count++
		}
	}
	return
}

func checkDiag1(input [][]byte, i, j int) (count int) {
	if i >= 3 && j < len(input[i])-3 {
		if input[i-1][j+1] == 'M' && input[i-2][j+2] == 'A' && input[i-3][j+3] == 'S' {
			count++
		}
	}
	if i < len(input)-3 && j >= 3 {
		if input[i+1][j-1] == 'M' && input[i+2][j-2] == 'A' && input[i+3][j-3] == 'S' {
			count++
		}
	}

	return
}

func checkDiag2(input [][]byte, i, j int) (count int) {
	if i >= 3 && j >= 3 {
		if input[i-1][j-1] == 'M' && input[i-2][j-2] == 'A' && input[i-3][j-3] == 'S' {
			count++
		}
	}
	if i < len(input)-3 && j < len(input[i])-3 {
		if input[i+1][j+1] == 'M' && input[i+2][j+2] == 'A' && input[i+3][j+3] == 'S' {
			count++
		}
	}
	return
}

func main_0() {
	file, err := os.ReadFile("./day4.txt")
	if err != nil {
		fmt.Printf("error reading file %w\n", err)
	}
	lines := bytes.Split(file, []byte{'\n'})
	lines = lines[0 : len(lines)-1]
	count := 0
	for i, row := range lines {
		for j, col := range row {
			if col == 'X' {
				count += checkRow(lines, i, j)
				count += checkCol(lines, i, j)
				count += checkDiag1(lines, i, j)
				count += checkDiag2(lines, i, j)
			}
		}
	}
	fmt.Printf("Part1 := %d\n", count)
	// Part1 := 2397

}
