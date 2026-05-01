import { TestBed } from '@angular/core/testing';

import { EvolutionMasseSalariale } from './evolution-masse-salariale';

describe('EvolutionMasseSalariale', () => {
  let service: EvolutionMasseSalariale;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EvolutionMasseSalariale);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
