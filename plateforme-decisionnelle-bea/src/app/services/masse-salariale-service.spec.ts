import { TestBed } from '@angular/core/testing';

import { MasseSalarialeService } from './masse-salariale-service';

describe('MasseSalarialeService', () => {
  let service: MasseSalarialeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MasseSalarialeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
