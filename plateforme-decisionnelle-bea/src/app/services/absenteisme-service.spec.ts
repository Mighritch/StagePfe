import { TestBed } from '@angular/core/testing';

import { AbsenteismeService } from './absenteisme-service';

describe('AbsenteismeService', () => {
  let service: AbsenteismeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AbsenteismeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
