package pl.waw.activeprogress.chesssolver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.waw.activeprogress.chesssolver.domain.Board;
import pl.waw.activeprogress.chesssolver.dto.MoveWithBoardDto;
import pl.waw.activeprogress.chesssolver.mapper.MoveWithBoardMapper;
import pl.waw.activeprogress.chesssolver.service.SolverService;

import java.util.List;

@RestController
@RequestMapping("v1/solution")
public class SolutionController {

    @Autowired
    SolverService solverService;
    @Autowired
    MoveWithBoardMapper moveWithBoardMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getSolution")
    public List<MoveWithBoardDto> getSolution(String fen, int inMoves) {

        return moveWithBoardMapper.mapToMoveWithBoardDtoList(solverService.getSolutionsForCheckmate(new Board(fen), inMoves));
    }

}
