package pl.waw.activeprogress.chesssolver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.waw.activeprogress.chesssolver.domain.Board;
import pl.waw.activeprogress.chesssolver.dto.MoveWithBoardDto;
import pl.waw.activeprogress.chesssolver.mapper.MoveWithBoardMapper;
import pl.waw.activeprogress.chesssolver.service.SolverService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/solution")
@CrossOrigin("*")
public class SolutionController {

    @Autowired
    SolverService solverService;
    @Autowired
    MoveWithBoardMapper moveWithBoardMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getSolution")
    public List<MoveWithBoardDto> getSolution(String fen, int inMoves) {
        System.out.print("Request: fen: " + fen + " | inMoves: " + inMoves);
        List<MoveWithBoardDto> result;
        try {
            long timeStart = System.currentTimeMillis();
            result = moveWithBoardMapper.mapToMoveWithBoardDtoList(solverService.getSolutionsForCheckmate(new Board(fen), inMoves));
            long timeStop = System.currentTimeMillis();
            System.out.println(" | solutions: " + result.size() + " | time: " + (timeStop - timeStart) + "ms");
        } catch (Exception e) {
            MoveWithBoardDto error = new MoveWithBoardDto("Error: " + e.getMessage());
            result = new ArrayList<>();
            result.add(error);
            System.out.println(" | error: " + e.getMessage());
        }
        return result;
    }

}
