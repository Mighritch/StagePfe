import { TestBed } from '@angular/core/testing';

import { SoldeCongeType } from './solde-conge-type';

describe('SoldeCongeType', () => {
  let service: SoldeCongeType;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SoldeCongeType);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
