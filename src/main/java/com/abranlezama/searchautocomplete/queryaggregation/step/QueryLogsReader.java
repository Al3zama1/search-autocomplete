package com.abranlezama.searchautocomplete.queryaggregation.step;

import com.abranlezama.searchautocomplete.queryaggregation.dto.QueryLogDTO;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class QueryLogsReader extends FlatFileItemReader<QueryLogDTO> {

    public QueryLogsReader() {
        setLineMapper(lineMapper());
        setResource(new FileSystemResource("./query-logs/user-queries.log"));
    }


    private LineMapper<QueryLogDTO> lineMapper() {
        DefaultLineMapper<QueryLogDTO> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");

        tokenizer.setNames("query", "time");
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper());

        return lineMapper;
    }



    private FieldSetMapper<QueryLogDTO> fieldSetMapper() {
        return fieldSet -> {
            QueryLogDTO queryLog = new QueryLogDTO();
            Instant instant = Instant.parse(fieldSet.readString("time"));

            queryLog.setQuery(fieldSet.readString("query"));
            queryLog.setTime(instant);

            return queryLog;
        };
    }

}
