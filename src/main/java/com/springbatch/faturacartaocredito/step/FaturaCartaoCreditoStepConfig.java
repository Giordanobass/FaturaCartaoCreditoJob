package com.springbatch.faturacartaocredito.step;


import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.faturacartaocredito.dominio.FaturaCartaoCredito;
import com.springbatch.faturacartaocredito.dominio.Transacao;
import com.springbatch.faturacartaocredito.reader.FaturaCartaoCreditoReader;
import com.springbatch.faturacartaocredito.writer.TotalTransacoesFooterCallback;

@Configuration
public class FaturaCartaoCreditoStepConfig {
    
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public Step faturaCartaoCretitoStep(
	    ItemStreamReader<Transacao> lerTransacaoReader,
	    ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> carregarDadosClienteProcessor,
	    ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCredito,
	    TotalTransacoesFooterCallback listener) {
	return stepBuilderFactory
		.get("faturaCartaoCreditoStep")
		.<FaturaCartaoCredito, FaturaCartaoCredito>chunk(1)
		.reader(new FaturaCartaoCreditoReader(lerTransacaoReader))
		.processor(carregarDadosClienteProcessor)
		.writer(escreverFaturaCartaoCredito)
		.listener(listener)
		.build();
    }

}
