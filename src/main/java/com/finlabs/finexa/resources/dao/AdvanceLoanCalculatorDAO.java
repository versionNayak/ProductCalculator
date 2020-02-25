package com.finlabs.finexa.resources.dao;

import java.util.List;

import com.finlabs.finexa.resources.model.AdvanceLoanCalculator;

public interface AdvanceLoanCalculatorDAO {
	public List<AdvanceLoanCalculator> getAdvanceLoanCalDetails(int clientId);
}