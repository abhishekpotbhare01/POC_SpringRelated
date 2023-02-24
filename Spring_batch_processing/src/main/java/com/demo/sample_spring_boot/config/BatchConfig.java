package com.demo.sample_spring_boot.config;

import com.demo.sample_spring_boot.listner.JobExecutionCompletionListener;
import com.demo.sample_spring_boot.model.Person;
import com.demo.sample_spring_boot.repository.PersonRepository;
import com.demo.sample_spring_boot.writer.PersonKafkaSender;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public PersonRepository personRepository;
    @Autowired
    public DataSource dataSource;

    @Bean
    public Job createJob() throws IOException, InterruptedException {
        return jobBuilderFactory.get("imports-users")
            .incrementer(new RunIdIncrementer())
            .start(stepBuilderFactory.get("csv-step")
                .<Person, Person>chunk(10)
                .reader(itemReader())
                .processor(processor())
                .writer(writer())
                .listener(new ItemWriteListener())
                .build()
            )
            .listener(new JobExecutionCompletionListener())
            .build();

    }

//    @Bean
//    public RepositoryItemWriter<Person> writer() throws InterruptedException {
//      //  Thread.sleep(1000);
//        RepositoryItemWriter<Person> writer = new RepositoryItemWriter<>();
//        writer.setRepository(personRepository);
//        writer.setMethodName("save");
//        return writer;
//    }

    @Bean
    public PersonKafkaSender writer() {
//        JdbcBatchItemWriter<Person> write = new JdbcBatchItemWriter<Person>();
//        write.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
//        write.setSql("INSERT INTO PERSON(FIRST_NAME,LAST_NAME,EMAIL,AGE)"
//            + " VALUES(:firstName,:lastName,:email,:age)");
//        write.setDataSource(dataSource);
//        return write;
        return new PersonKafkaSender();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public FlatFileItemReader<Person> itemReader() throws IOException {
        FlatFileItemReader<Person> itemReader = new FlatFileItemReader<>();
        //D:\person.csv
        itemReader.setResource(new FileSystemResource("D:\\person1.csv"));
        itemReader.setName("CSV reader");
        itemReader.setStrict(false);
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;

    }

    @Bean
    public LineMapper<Person> lineMapper() {
        DefaultLineMapper<Person> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("firstName", "lastName", "email", "age");

        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }
}
