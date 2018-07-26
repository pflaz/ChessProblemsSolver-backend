package pl.waw.activeprogress.chesssolver.mapper;

import org.springframework.stereotype.Component;
import pl.waw.activeprogress.chesssolver.domain.MoveWithBoard;
import pl.waw.activeprogress.chesssolver.dto.MoveWithBoardDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoveWithBoardMapper {

    public MoveWithBoardDto mapToMoveWithBoardDto(final MoveWithBoard moveWithBoard) {
        List<MoveWithBoard> nextMoves = moveWithBoard.getNextMoves();
        List<MoveWithBoardDto> nextMovesDto = null;
        if (nextMoves != null) {
            nextMovesDto = new ArrayList<>();
            for (int i = 0; i < nextMoves.size(); i++) {
                nextMovesDto.add(mapToMoveWithBoardDto(nextMoves.get(i)));
            }
        }
        return new MoveWithBoardDto(moveWithBoard.getMove().getName(), moveWithBoard.getMove().getShortNotation(), moveWithBoard.getMove().getLongNotation(), moveWithBoard.getBoard().getFen(), nextMovesDto);
    }

    public List<MoveWithBoardDto> mapToMoveWithBoardDtoList(final List<MoveWithBoard> moveWithBoardList) {
        return moveWithBoardList.stream()
                .map(moveWithBoard -> mapToMoveWithBoardDto(moveWithBoard))
                .collect(Collectors.toList());
    }
}
