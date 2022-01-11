package hu.idne.backend.models.system;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
public class PageResult<T> extends CountResult {

    private List<T> data = new ArrayList<>();

    public <R> PageResult<R> toView(Function<? super T, ? extends R> mapper) {
        PageResult<R> output = new PageResult<>();
        output.setRecordsTotal(this.recordsTotal);
        output.setRecordsFiltered(this.recordsFiltered);
        output.setData(this.data.stream().map(mapper).collect(Collectors.toList()));
        return output;
    }
}
