package com.misha.doctorapp.controllers;

import com.misha.doctorapp.entities.Ticket;
import com.misha.doctorapp.services.TimeslotsService;
import lombok.RequiredArgsConstructor;
import misha.doctorapp.generator.GetTicketsRequest;
import misha.doctorapp.generator.GetTicketsResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
@RequiredArgsConstructor
public class TimeslotsEndpoint {

    private static final String NAMESPACE_URI = "http://misha/doctorapp/generator";

    private final TimeslotsService timeslotsService;


    /**
     * Конечная точка для SOAP-сервиса, который создаёт талоны по заданным правилам.
     * Доступ осуществляется по URL: http://localhost:8080/api/v1/timeslots/generate, метод запроса: POST.
     * Возвращает сообщение о том, столько талонов было сгенерировано.
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTicketsRequest")
    @ResponsePayload
    public GetTicketsResponse getTickets(@RequestPayload GetTicketsRequest request) {
        GetTicketsResponse response = new GetTicketsResponse();
        try {
            List<Ticket> tickets = timeslotsService.generateTickets(request.getRules(), request.getDoctorId());
            response.setMessage("Сгенерировано " + tickets.size() + " талонов!");
        } catch (RuntimeException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }
}
