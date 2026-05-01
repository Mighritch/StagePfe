import { TestBed } from '@angular/core/testing';

import { SalaireParGrade } from './salaire-par-grade';

describe('SalaireParGrade', () => {
  let service: SalaireParGrade;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SalaireParGrade);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
