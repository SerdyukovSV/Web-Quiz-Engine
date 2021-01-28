package engine.service;

import engine.dto.CompletedQuizDto;
import engine.dto.UserDto;
import engine.model.CompletedQuiz;
import engine.model.User;
import engine.repository.CompletedRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CompletedQuizService.class)
class CompletedQuizServiceTest {

    @Autowired
    private CompletedQuizService service;
    @MockBean
    private CompletedRepository repository;
    @MockBean
    private UserService userService;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    void add_Should_Verify_RepositorySave() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        CompletedQuiz completedQuiz = new CompletedQuiz();

        when(userService.getCurrentUser()).thenReturn(userDto);

        service.add(completedQuiz, 1);

        verify(repository, times(1)).save(completedQuiz);
    }

    @Test
    void getAll_Should_Return_ListCompletedQuiz() {
        UserDto userDto = new UserDto();
        CompletedQuiz completedQuiz1 = new CompletedQuiz();
        CompletedQuiz completedQuiz2 = new CompletedQuiz();
        List<CompletedQuiz> list = Arrays.asList(completedQuiz1, completedQuiz2);

        userDto.setId(1);
        when(userService.getCurrentUser()).thenReturn(userDto);
        when(repository.findAllByUserIdOrderByCompletedAtDesc(1)).thenReturn(list);

        when(modelMapper.map(completedQuiz1, CompletedQuizDto.class))
                .thenReturn(new CompletedQuizDto());
        when(modelMapper.map(completedQuiz2, CompletedQuizDto.class))
                .thenReturn(new CompletedQuizDto());

        List<CompletedQuizDto> listDto = service.getAll();

        assertEquals(list.size(), listDto.size());
//        assertThat()
    }
}