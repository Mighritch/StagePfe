import { TestBed } from '@angular/core/testing';

import { PretsParService } from './prets-par-service';

describe('PretsParService', () => {
  let service: PretsParService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PretsParService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
